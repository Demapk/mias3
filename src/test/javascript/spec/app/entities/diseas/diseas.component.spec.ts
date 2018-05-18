/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MiasTestModule } from '../../../test.module';
import { DiseasComponent } from '../../../../../../main/webapp/app/entities/diseas/diseas.component';
import { DiseasService } from '../../../../../../main/webapp/app/entities/diseas/diseas.service';
import { Diseas } from '../../../../../../main/webapp/app/entities/diseas/diseas.model';

describe('Component Tests', () => {

    describe('Diseas Management Component', () => {
        let comp: DiseasComponent;
        let fixture: ComponentFixture<DiseasComponent>;
        let service: DiseasService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MiasTestModule],
                declarations: [DiseasComponent],
                providers: [
                    DiseasService
                ]
            })
            .overrideTemplate(DiseasComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DiseasComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DiseasService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Diseas(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.diseas[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
