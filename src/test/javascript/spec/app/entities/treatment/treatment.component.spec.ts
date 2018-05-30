/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MiasTestModule } from '../../../test.module';
import { TreatmentComponent } from '../../../../../../main/webapp/app/entities/treatment/treatment.component';
import { TreatmentService } from '../../../../../../main/webapp/app/entities/treatment/treatment.service';
import { Treatment } from '../../../../../../main/webapp/app/entities/treatment/treatment.model';

describe('Component Tests', () => {

    describe('Treatment Management Component', () => {
        let comp: TreatmentComponent;
        let fixture: ComponentFixture<TreatmentComponent>;
        let service: TreatmentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MiasTestModule],
                declarations: [TreatmentComponent],
                providers: [
                    TreatmentService
                ]
            })
            .overrideTemplate(TreatmentComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TreatmentComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TreatmentService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Treatment(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.treatments[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
