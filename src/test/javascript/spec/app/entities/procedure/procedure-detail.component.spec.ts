/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { MiasTestModule } from '../../../test.module';
import { ProcedureDetailComponent } from '../../../../../../main/webapp/app/entities/procedure/procedure-detail.component';
import { ProcedureService } from '../../../../../../main/webapp/app/entities/procedure/procedure.service';
import { Procedure } from '../../../../../../main/webapp/app/entities/procedure/procedure.model';

describe('Component Tests', () => {

    describe('Procedure Management Detail Component', () => {
        let comp: ProcedureDetailComponent;
        let fixture: ComponentFixture<ProcedureDetailComponent>;
        let service: ProcedureService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MiasTestModule],
                declarations: [ProcedureDetailComponent],
                providers: [
                    ProcedureService
                ]
            })
            .overrideTemplate(ProcedureDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProcedureDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProcedureService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Procedure(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.procedure).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
