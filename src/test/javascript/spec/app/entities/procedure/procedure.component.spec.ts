/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { MiasTestModule } from '../../../test.module';
import { ProcedureComponent } from '../../../../../../main/webapp/app/entities/procedure/procedure.component';
import { ProcedureService } from '../../../../../../main/webapp/app/entities/procedure/procedure.service';
import { Procedure } from '../../../../../../main/webapp/app/entities/procedure/procedure.model';

describe('Component Tests', () => {

    describe('Procedure Management Component', () => {
        let comp: ProcedureComponent;
        let fixture: ComponentFixture<ProcedureComponent>;
        let service: ProcedureService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MiasTestModule],
                declarations: [ProcedureComponent],
                providers: [
                    ProcedureService
                ]
            })
            .overrideTemplate(ProcedureComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProcedureComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProcedureService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Procedure(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.procedures[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
